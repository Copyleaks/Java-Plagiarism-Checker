# Changelog

All notable changes to the Copyleaks Java SDK are listed here.

## 5.2.0

The Maven coordinates (`com.copyleaks.sdk:copyleaks-java-sdk`) and the Java package names are the same as in 5.1.0. Existing code keeps working without changes. One exception: if your application re-serializes `CompletedWebhookModel` or `AlertsModel` with a bean-based serializer such as Jackson (for example by returning it from a Spring controller), the new getters show up as extra `aidetectionAlert` and `aidetectionResult` properties and can throw on malformed alert data. Exclude them, for example with a Jackson mix-in, as their Javadoc describes.

### Added

- **AI alert in the completed webhook (BAC-5392).**
  - `CompletedWebhookModel.getAIDetectionAlert()` returns the `suspected-ai-text` alert as an `AlertsModel`, or null when the scan has no AI alert.
  - `AlertsModel.getAIDetectionResult()` and the shortcut `CompletedWebhookModel.getAIDetectionResult()` decode the alert's `additionalData` JSON string into an `AIDetectionResponse`, with the summary, the classified sections and the model version.
  - They return null when there is no AI alert, when the data is empty, or when the data is JSON that is not an object. They throw Gson's `JsonSyntaxException` when `additionalData` is malformed JSON. Trailing NUL and whitespace characters are removed before parsing.
  - The raw string is still available from `AlertsModel.getAdditionalData()`.
  - New constant `models.constants.CopyleaksAlertCodes.SUSPECTED_AI_TEXT` (`"suspected-ai-text"`).
  - The README and the `WebhookController` example show how to read the AI result in a completed webhook handler.
- **More fields on the AI Text Detection result (BAC-5392).**
  - `AIDetectionResponse.getTranslationProvider()` and `getTranslation()`: set when the text was machine-translated to English before detection. Otherwise `getTranslationProvider()` returns 0 or null and `getTranslation()` returns null.
  - `AIDetectionResponse.getExplain()`: the AI Logic explanation, with the new `Explain`, `Patterns` and `PatternStatistics` classes. Null when AI Logic was not enabled.
  - `Chars.getGroupIds()` and `Words.getGroupIds()` (the HTML group identifiers) and `Match.getHtml()` (the match in the HTML version of the document). Null for non-HTML sources.

### Fixed

- **AI Text Detection results from sandbox scans (BAC-5392).** Sandbox scans send some keys in PascalCase (`Human`, `Ai`, `Starts`, `Lengths`). The SDK now reads them, so `Summary.getHuman()`, `Summary.getAi()` and the start and length positions are filled in instead of reading 0 or null.
- **Javadoc for `Copyleaks.login`, `Copyleaks.getReleaseNotes` and `Copyleaks.getSupportedFileTypes` (BAC-6759).** `login` now documents each exception it throws, and the two other methods document their return value.

### Changed

- **README (BAC-5374).** The README now describes Copyleaks as a platform and lists the products this SDK covers: AI Text Detection, AI Image Detection, Plagiarism Checker, Grammar Checker and Text Moderation. Section names follow the product names, for example "AI Text Detection", "Grammar Checker (WritingAssistantClient)" and "AI Image Detection". Class and method names did not change.
- **Build (BAC-6759).** The release build now works with Maven 3.10 on JDK 17 and newer. `maven-javadoc-plugin` moved from 2.9.1 to 3.12.0, and `nexus-staging-maven-plugin` from 1.6.7 to 1.7.0. The Javadoc now links to the Java 11 API docs, matching the SDK's Java version. The release still ships the main, `-sources` and `-javadoc` jars. There is no change to the SDK's runtime behavior.
