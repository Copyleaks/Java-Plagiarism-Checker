# Changelog

All notable changes to the Copyleaks Java SDK are listed here.

## 6.0.0

This is a major release. The Maven coordinates (`com.copyleaks.sdk:copyleaks-java-sdk`) and the Java package names are the same as in 5.1.0, and Java 11 or later is still required. A few changes can break existing code or change results. They are listed under [Upgrade notes](#upgrade-notes).

### Added

- **New endpoints.**
  - Image Plagiarism Detection: `Copyleaks.imagePlagiarismClient.check(token, scanId, CopyleaksImagePlagiarismRequestModel)` uploads an image as `multipart/form-data` and returns the full and partial matches found on the web (`CopyleaksImagePlagiarismResponseModel`).
  - AI Video Detection: `Copyleaks.aiVideoDetectionClient.submit(token, scanId, CopyleaksAiVideoDetectionRequestModel)` submits a video URL. Parse the webhook result into `CopyleaksAiVideoDetectionResponseModel`. New constant `CopyleaksAiVideoDetectionModels.AI_VIDEO_1_PRO`.
  - AI Image Detection for an image URL: `Copyleaks.aiImageDetectionClient.submitAsync(token, scanId, CopyleaksAiImageDetectionAsyncRequestModel)`. The webhook result has the same shape as `CopyleaksAiImageDetectionResponseModel`. Both async requests use the new `CopyleaksAsyncWebhooksModel` for the webhook URL and headers.
  - AI Text Detection credit balance: `Copyleaks.aiDetectionClient.getCredits(token)`.
  - Allowed cross-language scan languages: `Copyleaks.getAllowedCrossLanguages()`. Each list maps the language name to its ISO-639-1 code, as the API returns it; `getDocumentLanguageCodes()` and `getResultLanguageCodes()` return the codes only.
  - File types supported by AI Text Detection: `Copyleaks.getSupportedAiTextDetectionFileTypes()`.
  - Private Cloud Hub repository information: `Copyleaks.getRepositoryInfo(token, repositoryId)`.
- **AI Image Detection with a file upload.** `Copyleaks.aiImageDetectionClient.submitFile(token, scanId, CopyleaksAiImageDetectionFileRequestModel)` sends the image bytes as `multipart/form-data`, without base64 encoding.
- **New request options.**
  - Scan properties: `scanTimeZone`, `references.validate` (Citation Checker, `SubmissionReferences`), `internalAiSourceMatch.enable` (`SubmissionInternalAiSourceMatch`), `webhooks.statusHeaders` and `webhooks.newResultHeaders`, `scanning.exclude.backlinksDomains` and `scanning.exclude.text`, and `pdf.colors.aiDetection` and `pdf.colors.writingFeedback`. `SubmissionPdfVersion.V3`.
  - Setters and constructors for values that could not be set before: the `id` of `SubmissionAuthor`, `SubmissionCourse`, `SubmissionAssignment` and `SubmissionInstitution`, `idPattern` of `SubmissionScanningExclude` and `SubmissionScanningInclude`, `SubmissionIndexing.setRepositories` and `SubmissionWebhooks.setStatus`.
  - Submit URL: `verb` and `headers` on `CopyleaksURLSubmissionModel`.
  - Delete: `headers` for the completion webhook on `CopyleaksDeleteRequest`.
  - Export: `completionWebhookHeaders` and the `overview` report (`ExportOverview`). The new constructor `CopyleaksExportModel(completionWebhook)` and the setters for `results` and `crawledVersion` let you export only the reports you need.
  - AI Text Detection: `explain` (AI Logic) and `sensitivity` (1 to 3) on `CopyleaksNaturalLanguageSubmissionModel`.
  - AI Image Detection: `maskType` (`CopyleaksAiImageMaskTypes`: binary, heatmap, none) and `objectDetection` (`CopyleaksObjectDetectionItem`).
- **New response and webhook fields.**
  - AI Image Detection: `isAiDetected`, `detectedObjects`, `result.labels` and `imageInfo.metadata.contentSummary`.
  - Error webhook: `error.type`, `error.id`, `error.url` and `error.details`.
  - Completed webhook: `referencesValidation` (the Citation Checker summary) and `results.internalAIData`.
  - The AI alert in the completed webhook: `CompletedWebhookModel.getAIDetectionAlert()` returns the `suspected-ai-text` alert, and `AlertsModel.getAIDetectionResult()` and the shortcut `CompletedWebhookModel.getAIDetectionResult()` decode its `additionalData` into an `AIDetectionResponse`. They return null when there is no AI alert, when the data is empty, or when the data is JSON that is not an object, and throw Gson's `JsonSyntaxException` when the data is malformed JSON. New constant `CopyleaksAlertCodes.SUSPECTED_AI_TEXT`.
  - AI Text Detection: `AIDetectionResponse.getTranslationProvider()`, `getTranslation()` and `getExplain()` (with the new `Explain`, `Patterns` and `PatternStatistics` classes), `Chars.getGroupIds()`, `Words.getGroupIds()` and `Match.getHtml()`.
- **HTTP settings.** `Copyleaks.setConnectTimeout`, `setRequestTimeout`, `setMaxConcurrentRequests`, `setMaxRetries` and `setHttpClient`, with matching getters. Retries are off by default. When turned on, every request is retried on 429 and 503, and GET requests also on 502, 504 and network errors, with exponential backoff, jitter and the `Retry-After` header.
- **Error details on exceptions.** `CommandException`, `RateLimitException` and `UnderMaintenanceException` have `getStatusCode()` and `getResponseBody()`. `RateLimitException.getRetryAfter()` returns the wait from the `Retry-After` header.
- `SupportedFileTypesResponse.getTextual()`, `TaskModel.getEndpoint()`, `CopyleaksDeleteRequest.getCompletionWebhook()`, `CopyleaksTextModerationRequest.MAX_LABELS` and getters on the submission models.

### Changed

- **Every request now has a timeout.** The defaults are 30 seconds to connect and 120 seconds for the whole request, including the wait for a free concurrency slot and reading the response. A timeout throws `ExecutionException` caused by `java.net.http.HttpTimeoutException`. Before, a request had no timeout.
- **429 is reported the same way everywhere.** Every method throws `RateLimitException` on 429. Before, submit, the AI Text Detection, AI Image Detection, Grammar Checker and Text Moderation clients threw `CommandException`.
- The `CommandException` message now contains the HTTP status and the response body.
- Request enums are sent as the integers the API documents, for example `"action": 2`. When reading JSON, both the integer and the constant name are accepted.
- `ScoreWeights` weights default to 1.0, the API default. Before, unset weights were sent as 0.0.
- AI Image Detection sends the file name as `filename`, as documented.
- `getSupportedFileTypes()` calls the documented path `/v3/miscellaneous/supported-plagiarism-file-types`.
- Path values such as scan IDs are URL-encoded.
- Gson is upgraded from 2.8.6 to 2.13.2, which fixes CVE-2022-25647.
- The User-Agent is now `java-sdk/6.0.0`.
- Deprecated: `SubmissionProperties.getExperation()` and `setExperation()` (use `getExpiration()` and `setExpiration()`), `SupportedFileTypesResponse.getTextural()` (use `getTextual()`), `TaskModel.getEndpont()` (use `getEndpoint()`), `CopyleaksDeleteRequest.getUri()` (use `getCompletionWebhook()`), `Result.getProbability()` (the API deprecated it) and the client constructors that take an `HttpClient` and a `Semaphore`.

### Fixed

- **A hung request could block the JVM.** All requests shared 20 permits with no timeout, so 20 hung calls blocked every later SDK call forever. Requests are now bounded by the request timeout and always release their permit.
- **Delete ignored the completion webhook.** The URL was sent as `uri` and never registered. It is now sent as `completionWebhook`.
- **`SubmissionActions.IndexOnly` was rejected with 400.** Enums were sent as names, and the API has no `IndexOnly` name. All request enums are now sent as integers.
- **`customMetadata` could not be used.** It was a single object with a package-private constructor, but the API expects an array. It is now `SubmissionCustomMetadata[]` and the constructor is public.
- **Repository results threw `ClassCastException`.** `RepositoriesModel.getMetadata()` and `NewResultsRepositoriesModel.getMetadata()` now return the metadata, including `submittedBy`.
- **Setting one Grammar Checker weight set the other three to 0.0.** The weights now default to 1.0.
- **`setExperation` was sent as an unknown property and ignored.** It now sets `expiration`.
- **The export-completed task endpoint was always null** because of a misspelled field. `TaskModel.getEndpoint()` now returns it.
- **The credits-checked webhook lost `credits` and `metadata`** of the scanned document.
- **Text Moderation allowed up to 32 labels**, but the API accepts at most 16. It also accepted an empty list. Both are now rejected before the request is sent.
- **Error bodies were lost.** Exceptions showed the internal future instead of the server response.
- **AI Text Detection results from sandbox scans.** Sandbox scans send some keys in PascalCase (`Human`, `Ai`, `Starts`, `Lengths`). The SDK now reads them, so `Summary.getHuman()`, `Summary.getAi()` and the start and length positions are filled in instead of reading 0 or null.
- Javadoc links point to the current API documentation, and every public method documents the exceptions it throws.

### Upgrade notes

- **Spring Boot is no longer a dependency.** 5.x pulled `spring-boot-starter-web` (with Spring, Tomcat, Jackson and Logback) into every application. The SDK now depends only on Gson. If your application used those libraries without declaring them, add them to your own build.
- **The `example` package is no longer in the jar.** The examples moved to the separate `examples` project in the repository, which is not published.
- **Methods that now declare `RateLimitException`:** `Copyleaks.submitFile`, `submitFileOcr` and `submitUrl`, `AIDetectionClient.submitNaturalLanguage`, `AiImageDetectionClient.submit`, `WritingAssistantClient.submitText` and `TextModerationClient.submitText`. Code that catches specific exceptions must also catch `RateLimitException`, or `Exception`.
- **`SubmissionProperties.getCustomMetadata()` and `setCustomMetadata()` take an array:** `SubmissionCustomMetadata[]` instead of `SubmissionCustomMetadata`.
- **`CreditsCheckedWebhookModel.getScannedDocument()` returns `ScannedDocumentModel`** (the scanned document of the completed webhook, with `getCredits()` and `getMetadata()`) instead of `models.response.aidetection.ScannedDocument`.
- **Calls that take longer than 120 seconds now fail.** Raise the limit with `Copyleaks.setRequestTimeout` if you submit very large files over slow connections.
- **Grammar Checker weights you do not set are now 1.0**, so the overall score can change. Set a weight to 0.0 explicitly to leave a category out.
- **Text Moderation requests need 1 to 16 labels.** More than 16, or none, throws `IllegalArgumentException`.
- **Delete now registers the completion webhook** you pass as the third constructor argument, so your endpoint starts receiving the call.
- **Gson is upgraded to 2.13.2.** If your build pins an older Gson, remove the pin or use 2.13.2 or later.
- **Code that parses `CommandException.getMessage()`** should use `getStatusCode()` and `getResponseBody()` instead.
- **If you re-serialize SDK models with a bean-based serializer** such as Jackson (for example by returning them from a Spring controller), the computed getters `getAIDetectionAlert()` and `getAIDetectionResult()` show up as extra properties and can throw on malformed alert data. Exclude them, for example with a Jackson mix-in. The AI Image Detection models no longer carry Jackson annotations; their field names match the JSON, so Jackson still maps them.
