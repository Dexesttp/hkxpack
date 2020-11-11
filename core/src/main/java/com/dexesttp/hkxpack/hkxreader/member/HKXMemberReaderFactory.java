package com.dexesttp.hkxpack.hkxreader.member;

import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkxreader.HKXObjectReader;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.PointerNameGenerator;
import com.dexesttp.hkxpack.hkxreader.member.arrays.HKXArrayContentsReader;
import com.dexesttp.hkxpack.hkxreader.member.arrays.HKXArrayContentsReaderFactory;
import com.dexesttp.hkxpack.l10n.SBundle;

/**
 * Creates the relevant {@link HKXMemberReader} from a given
 * {@link HKXMemberTemplate}.
 */
public class HKXMemberReaderFactory {
	private final transient HKXDescriptorFactory descriptorFactory;
	private final transient HKXReaderConnector connector;
	private final transient PointerNameGenerator generator;
	private final transient HKXEnumResolver enumResolver;
	private transient HKXObjectReader objectCreator;
	private transient HKXArrayContentsReaderFactory acrFactory;

	/**
	 * Creates a {@link HKXMemberReaderFactory}
	 * 
	 * @param descriptorFactory the {@link HKXDescriptorFactory} to use.
	 * @param connector         the {@link HKXReaderConnector} that links to the
	 *                          file.
	 * @param generator         the {@link PointerNameGenerator} to create pointer
	 *                          names with.
	 * @param enumResolver      the {@link HKXEnumResolver} to use, should be
	 *                          connected to the {@link HKXDescriptorFactory}.
	 * @see HKXMemberReaderFactory#connectObjectCreator(HKXObjectReader)
	 */
	public HKXMemberReaderFactory(final HKXDescriptorFactory descriptorFactory, final HKXReaderConnector connector,
			final PointerNameGenerator generator, final HKXEnumResolver enumResolver) {
		this.descriptorFactory = descriptorFactory;
		this.connector = connector;
		this.generator = generator;
		this.enumResolver = enumResolver;
	}

	/**
	 * Connect an object creator to the {@link HKXMemberReaderFactory}.
	 * <p>
	 * This method is required after the {@link HKXMemberReaderFactory} is created.
	 * However, as the connection between a {@link HKXObjectReader} and a
	 * {@link HKXMemberReaderFactory} is cyclic, this is a distinct method from the
	 * constructor.
	 * 
	 * @param objectCreator
	 */
	public void connectObjectCreator(final HKXObjectReader objectCreator) {
		this.objectCreator = objectCreator;
		this.acrFactory = new HKXArrayContentsReaderFactory(connector, descriptorFactory, objectCreator, generator);
	}

	/**
	 * Retrieves the relevant {@link HKXMemberReader} from a
	 * {@link HKXMemberTemplate}.
	 * 
	 * @param template the {@link HKXMemberTemplate} to resolve.
	 * @return the relevant {@link HKXMemberReader}.
	 * @throws ClassFileReadException if there was an error resolving the template.
	 */
	public HKXMemberReader getMemberReader(final HKXMemberTemplate template) throws ClassFileReadException {
		switch (template.vtype.getFamily()) {
		case DIRECT:
		case COMPLEX:
			return new HKXDirectMemberReader(connector, template.name, template.vtype, template.offset);
		case ENUM:
			return new HKXEnumMemberReader(connector, enumResolver, template.name, template.vtype, template.vsubtype,
					template.target, template.offset);

		case ARRAY:
			HKXArrayContentsReader arrayContentsReader = acrFactory.get(template);
			if (template.vtype == HKXType.TYPE_RELARRAY) {
				return new HKXRelArrayMemberReader(connector, template.name, template.vsubtype, arrayContentsReader,
						template.offset);
			} else {
				return new HKXArrayMemberReader(connector, template.name, template.vsubtype, arrayContentsReader,
						template.offset);
			}
		case OBJECT:
			HKXDescriptor descriptor = descriptorFactory.get(template.target);
			return new HKXObjectMemberReader(objectCreator, template.name, template.offset, descriptor);
		case POINTER:
			return new HKXPointerMemberReader(connector, generator, template.name, template.vsubtype, template.offset);
		case STRING:
			return new HKXStringMemberReader(connector, template.name, template.vtype, template.offset);
		default:
			throw new IllegalArgumentException(SBundle.getString("error.hkx.read.type") + template.vtype);
		}
	}
}
