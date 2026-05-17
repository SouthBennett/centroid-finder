# Video Options
## 1. JavaCV
- Pros
-- Easy Setup
-- Great video support.
-- Good at Frame-by-Frame Processing
-- Works with Maven
-- Friendly to Java users
- Cons
-- Frame conversion could be tough to understand
-- Complex threading
-- There migh be dependency conflicts


## 2. JCodec

JCodec is a pure Java video library. It can decode some video formats and extract frames without needing native FFmpeg installed.

Pros:
- Pure Java, we are used to java more than other libraries and code right now.
- Easier dependency setup.
- Good if we only need simple frame extraction.

Cons:
- Supports fewer video formats.
- May struggle with some modern mp4 videos.
- Documentation/examples can might be limited.