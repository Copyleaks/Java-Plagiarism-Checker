# Changelog

All notable changes to the Copyleaks Java SDK are listed here.

## 5.2.0

The Maven coordinates (`com.copyleaks.sdk:copyleaks-java-sdk`) and the Java package names are the same as in 5.1.0. Existing code keeps working without changes. One exception: if your application re-serializes `CompletedWebhookModel` or `AlertsModel` with a bean-based serializer such as Jackson (for example by returning it from a Spring controller), the new getters show up as extra `aidetectionAlert` and `aidetectionResult` properties and can throw on malformed alert data. Exclude them, for example with a Jackson mix-in, as their Javadoc describes.

### Added

- **AI alert in the completed webhook.**
  - `CompletedWebhookModel.getAIDetectionAlert()` returns the `suspected-ai-text` alert as an `AlertsModel`, or null when the scan has no AI alert.
  - `AlertsModel.getAIDetectionResult()` and the shortcut `CompletedWebhookModel.getAIDetectionResult()` decode the alert's `additionalData` JSON string into an `AIDetectionResponse`, with the summary, the classified sections and the model version.
  - They return null when there is no AI alert, when the data is empty, or when the data is JSON that is not an object. They throw Gson's `JsonSyntaxException` when `additionalData` is malformed JSON. Trailing NUL and whitespace characters are removed before parsing.
  - The raw string is still available from `AlertsModel.getAdditionalData()`.
  - New constant `models.constants.CopyleaksAlertCodes.SUSPECTED_AI_TEXT` (`"suspected-ai-text"`).
  - The README and the `WebhookController` example show how to read the AI result in a completed webhook handler.
- **More fields on the AI Text Detection result.**
  - `AIDetectionResponse.getTranslationProvider()` and `getTranslation()`: set when the text was machine-translated to English before detection. Otherwise `getTranslationProvider()` returns 0 or null and `getTranslation()` returns null.
  - `AIDetectionResponse.getExplain()`: the AI Logic explanation, with the new `Explain`, `Patterns` and `PatternStatistics` classes. Null when AI Logic was not enabled.
  - `Chars.getGroupIds()` and `Words.getGroupIds()` (the HTML group identifiers) and `Match.getHtml()` (the match in the HTML version of the document). Null for non-HTML sources.

### Fixed

- **AI Text Detection results from sandbox scans.** Sandbox scans send some keys in PascalCase (`Human`, `Ai`, `Starts`, `Lengths`). The SDK now reads them, so `Summary.getHuman()`, `Summary.getAi()` and the start and length positions are filled in instead of reading 0 or null.
- **Javadoc for `Copyleaks.login`, `Copyleaks.getReleaseNotes` and `Copyleaks.getSupportedFileTypes`.** `login` now documents each exception it throws, and the two other methods document their return value.
