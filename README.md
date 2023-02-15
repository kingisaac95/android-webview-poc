# android-webview-poc

The goal of this PoC is to investigate and come up with a new, simpler, and more maintainable architecture for LinkSDK wrappers which would provided a consistent interaction between web SDK and mobile SDKs.

- Test that we can call the hosted HTML with params and initiate a flow.
- Explore if we can make some modification to the callbacks from link-sdk-web to send back redirect URLs instead (ie deep-linking)