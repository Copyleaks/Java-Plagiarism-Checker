# Copyleaks Java SDK

Copyleaks SDK enables you to scan text for plagiarism and detect content distribution online, using the Copyleaks plagiarism checker API.

Using Copyleaks SDK you can check for plagiarism in:
* Online content and webpages
* Local and cloud files (see [supported files](https://api.copyleaks.com/documentation/specifications#2-supported-file-types))
* Free text
* OCR (Optical Character Recognition) - scanning pictures with textual content (see [supported files](https://api.copyleaks.com/documentation/specifications#6-supported-image-types-ocr))

## Installation

Supported Java version: 11 and above.

Download the code from this repository and add it to your project.

OR

Download the latest version from Maven Central Repository:
https://search.maven.org/artifact/com.copyleaks.sdk/copyleaks-java-sdk

## Register and Get Your API Key
To use the Copyleaks API you need to first be a registered user. Creating a Copyleaks account takes a minute and is free of charge. [Signup](https://api.copyleaks.com/?register=true) and make sure to confirm your account with the activation email.

As a registered user you can generate your personal API key. You can do so at your [dashboard home](https://api.copyleaks.com/dashboard) under 'API Access Credentials'.

For more information check out our [API guide](https://api.copyleaks.com/documentation/v3).

## Examples
See the [Example.java](https://github.com/Copyleaks/Java-Plagiarism-Checker/blob/master/src/main/java/example/Example.java) file.

* (Option) To change the Identity server URI (default:"https://id.copyleaks.com"):
```rb
Copyleaks.setIdentityUri("<your identity server uri>");
```
* (Option) To change the API server URI (default:"https://api.copyleaks.com"):
```rb
Copyleaks.setApiUri("<your api server uri>");
```
## Dependency
* [Gson](https://github.com/google/gson)

## Read More
* [API Homepage](https://api.copyleaks.com/)
* [API Documentation](https://api.copyleaks.com/documentation)
* [Plagiarism Report](https://github.com/Copyleaks/plagiarism-report)
