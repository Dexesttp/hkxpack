package com.dexesttp.hkxpack.hkxreader.member.arrays;

import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkxreader.HKXObjectReader;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.PointerNameGenerator;
import com.dexesttp.hkxpack.l10n.SBundle;

/**
 * Retrieves the relevant {@link HKXArrayContentsReader} for a given {@link HKXMemberTemplate}.
 */
public class HKXArrayContentsReaderFactory {
	private final transient HKXReaderConnector connector;
	private final transient HKXDescriptorFactory descriptorFactory;
	private final transient HKXObjectReader objectReader;
	private final transient PointerNameGenerator pointerNameGenerator;

	/**
	 * Creates a {@link HKXArrayContentsReaderFactory}.
	 * @param connector the {@link HKXReaderConnector} connected to the relevant HKX file.
	 * @param descriptorFactory the {@link HKXDescriptorFactory} to use.
	 * @param objectReader the {@link HKXObjectReader} to use.
	 * @param pointerNameGenerator the {@link PointerNameGenerator} to use.
	 */
	public HKXArrayContentsReaderFactory(final HKXReaderConnector connector, final HKXDescriptorFactory descriptorFactory,
			final HKXObjectReader objectReader, final PointerNameGenerator pointerNameGenerator) {
		this.connector = connector;
		this.descriptorFactory = descriptorFactory;
		this.objectReader = objectReader;
		this.pointerNameGenerator = pointerNameGenerator;
	}
	
	/**
	 * Retrieves a {@link HKXArrayContentsReader} based on a {@link HKXMemberTemplate}.
	 * @param template the {@link HKXMemberTemplate} to base the reader on.
	 * @return the relevant {@link HKXArrayContentsReader}
	 * @throws ClassFileReadException if there was an error resolving the {@link HKXMemberTemplate}.
	 */
	public HKXArrayContentsReader get(final HKXMemberTemplate template) throws ClassFileReadException {
		switch(template.vsubtype.getFamily()) {
			case DIRECT:
			case COMPLEX:
				return new HKXDirectArrayContentsReader(connector, template.vsubtype);
			case STRING:
				return new HKXStringArrayContentsReader(connector, template.vsubtype);
			case OBJECT:
				HKXDescriptor descriptor = descriptorFactory.get(template.target);
				return new HKXObjectArrayContentsReader(objectReader, descriptorFactory, descriptor);
			case POINTER:
				return new HKXPointerArrayContentsReader(connector, pointerNameGenerator);
			default:
				throw new IllegalArgumentException(SBundle.getString("error.hkx.read.subtype") + template.vsubtype);
		}
	}
}
