package hurricane.jsonld;

import hurricane.jsonld.model.JsonLdContext;
import hurricane.jsonld.model.JsonLdDictionary;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class JsonLdProcessor {
  public static CompletionStage<JsonLdDictionary> compact(JsonLdInput input, JsonLdContext context) {
    return compact(input, context, JsonLdOptions.build());
  }

  public static CompletionStage<JsonLdDictionary> compact(JsonLdInput input, JsonLdContext context, JsonLdOptions options) {
    return null;
  }

  public static CompletionStage<List<JsonLdDictionary>> expand(JsonLdInput input) {
    return expand(input, JsonLdOptions.build());
  }

  public static CompletionStage<List<JsonLdDictionary>> expand(JsonLdInput input, JsonLdOptions options) {
    return null;
  }

  public static CompletionStage<JsonLdDictionary> flatten(JsonLdInput input) {
    return flatten(input, JsonLdContext.empty(), JsonLdOptions.build());
  }

  public static CompletionStage<JsonLdDictionary> flatten(JsonLdInput input, JsonLdContext context) {
    return flatten(input, context, JsonLdOptions.build());
  }

  public static CompletionStage<JsonLdDictionary> flatten(JsonLdInput input, JsonLdOptions options) {
    return flatten(input, JsonLdContext.empty(), options);
  }

  public static CompletionStage<JsonLdDictionary> flatten(JsonLdInput input, JsonLdContext context, JsonLdOptions options) {
    return null;
  }

}
