# Contribution rules

## Scope

These rules are guidelines for contributions. They're not exclusive conditions for a contribution to be accepted, but should be enforced as often as possible.


## Contribution process

A contribution should always go through a Pull Request, ideally using GitHub's Pull Request feature.

The Pull Request should be used to discuss the scope of the contribution, eventual issues and finally approval.

#### Pull Request template

A pull request should be written in the following format :

```markdown
# <NAME>
## Scope
<Description of the Pull Request changes>

## Additions
 - <Added item>  
<Eventual addition description>
 - <Added item>
 
## Fixes
 - <Fixed item>  
<Eventual fix description>
 - <Fixed item>

## Notes
 - <Note 1>
 - <Note 2>
```

#### Target branch

The Pull Request should be directed either towards the "dev" branch or any relevant "task/" branch of the repository.


## Contributed code

There is a number of conditions the contributed code should respect.

#### Unit and integration tests
Unit and integration tests are integral parts of the code, and should be stored under `/src/tests` for each module.

There is no separation between unit and integration tests.

If you change the behavior of part of the code, you should try to change the relevant tests to adapt to the new behavior.

If a test fail, **don't** add the `@Ignore` annotation or delete/comment the test. Try to fix either the test or the code.

When possible, for evey API additions add either unit or integration tests.

Document any tests changes in the Pull Request, and be prepared to answer questions about why the test changed or was deleted.

#### Comments
Comments in the code should help break down a complex behavior. If the behavior is too complex, try to break it down into smaller, easier to understand pieces of code.

Don't comment out a deprecated method. Either add a `@deprecated` tag or delete the method. Versioning (git) should take care of keeping the old code.

#### JavaDoc and other documentation
Try to document your code. If you don't want to document your additions, at least change the documentation of any modified items.

If your method requires excessive JavaDoc reading to be understood, consider changing its name.
JavaDoc should assist understanding the method, not be required to understand it.


## Release process

A release should be done from the `dev` branch to `master`.  
It should be done via a Pull Request, named as the new version and following the Pull Request format.  
A release whould always be accompanied with a version change, but the reverse is not true : the version can change without a release.

After being merged, a release should be officially released using the GitHub release option.

#### Version name

The version name convention is as follows : \<n1\>.\<n2\>.\<n3\>[-\<indicator\>]

 - First number : major version  
Represents a major version. If this version change, the API is expected to break.  
While this value is 0, the version is considered "tentative" and the API is expected to break between minor or state versions.

 - Second number : minor version  
Represents a minor version. A change of minor version should represent additions to the API, but no behavior changes to the existing API.

 - Third number : version state  
Represents both the state of the release and eventual changes.  
**If the third number is even :** An even third digit represents a stable version.  
**If the third number is odd :** An odd third digit represents an unstable version, with eventual broken features. It should be used exclusively in pre-releases.

 - (optional) linguistic indicator  
Liguistic representation of the software state. Is mostly used either in odd third numbers or when the first number is 0.  
This value can be `alpha`, `beta`, `indev`, `gold` or any state indicator.  
This value **can't** be `hotfix` or `revert`, as these are version changes indicator. This information goes in the pull request.

#### Releases

Releases should be done using the same text of the release's Pull Request.
It should also incude a compiled target for the command line interface, as well as a compiled target for the core and any eventual future component.
