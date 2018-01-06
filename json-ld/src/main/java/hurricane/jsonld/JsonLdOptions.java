package hurricane.jsonld;

import hurricane.core.iri.IriReference;
import hurricane.jsonld.model.JsonLdContext;
import java.util.Objects;

public final class JsonLdOptions {
  private final IriReference base;
  private final boolean compactArrays;
  private final LoadDocumentCallback documentLoader;
  private final JsonLdContext expandContext;
  private final boolean produceGeneralizedRdf;
  private final String processingMode;
  private final boolean compactToRelative;

  private JsonLdOptions(
      final IriReference base,
      final boolean compactArrays,
      final LoadDocumentCallback documentLoader,
      final JsonLdContext expandContext,
      final boolean produceGeneralizedRdf,
      final String processingMode,
      final boolean compactToRelative
  ) {
    this.base = base;
    this.compactArrays = compactArrays;
    this.documentLoader = documentLoader;
    this.expandContext = expandContext;
    this.produceGeneralizedRdf = produceGeneralizedRdf;
    this.processingMode = processingMode;
    this.compactToRelative = compactToRelative;
  }

  public IriReference getBase() {
    return base;
  }

  public boolean isCompactArrays() {
    return compactArrays;
  }

  public LoadDocumentCallback getDocumentLoader() {
    return documentLoader;
  }

  public JsonLdContext getExpandContext() {
    return expandContext;
  }

  public boolean isProduceGeneralizedRdf() {
    return produceGeneralizedRdf;
  }

  public String getProcessingMode() {
    return processingMode;
  }

  public boolean isCompactToRelative() {
    return compactToRelative;
  }

  public static Builder withBase(final IriReference base) {
    return new Builder().withBase(base);
  }

  public static Builder withCompactArrays(final boolean compactArrays) {
    return new Builder().withCompactArrays(compactArrays);
  }

  public static Builder withDocumentLoader(final LoadDocumentCallback documentLoader) {
    return new Builder().withDocumentLoader(documentLoader);
  }

  public static Builder withExpandContext(final JsonLdContext expandContext) {
    return new Builder().withExpandContext(expandContext);
  }

  public static Builder withProduceGeneralizedRdf(final boolean produceGeneralizedRdf) {
    return new Builder().withProduceGeneralizedRdf(produceGeneralizedRdf);
  }

  public static Builder withProcessingMode(final String processingMode) {
    return new Builder().withProcessingMode(processingMode);
  }

  public static Builder withCompactToRelative(final boolean compactToRelative) {
    return new Builder().withCompactToRelative(compactToRelative);
  }

  public static JsonLdOptions build() {
    return new Builder().build();
  }

  public static class Builder {
    private IriReference base = null;
    private boolean compactArrays = true;
    private LoadDocumentCallback documentLoader = null;
    private JsonLdContext expandContext = null;
    private boolean produceGeneralizedRdf = true;
    private String processingMode = null;
    private boolean compactToRelative = true;

    public Builder withBase(final IriReference base) {
      this.base = Objects.requireNonNull(base);
      return this;
    }

    public Builder withCompactArrays(final boolean compactArrays) {
      this.compactArrays = compactArrays;
      return this;
    }

    public Builder withDocumentLoader(final LoadDocumentCallback documentLoader) {
      this.documentLoader = Objects.requireNonNull(documentLoader);
      return this;
    }

    public Builder withExpandContext(final JsonLdContext expandContext) {
      this.expandContext = Objects.requireNonNull(expandContext);
      return this;
    }

    public Builder withProduceGeneralizedRdf(final boolean produceGeneralizedRdf) {
      this.produceGeneralizedRdf = produceGeneralizedRdf;
      return this;
    }

    public Builder withProcessingMode(final String processingMode) {
      this.processingMode = Objects.requireNonNull(processingMode);
      return this;
    }

    public Builder withCompactToRelative(final boolean compactToRelative) {
      this.compactToRelative = compactToRelative;
      return this;
    }

    public JsonLdOptions build() {
      return new JsonLdOptions(
          base,
          compactArrays,
          documentLoader,
          expandContext,
          produceGeneralizedRdf,
          processingMode,
          compactToRelative
      );
    }
  }
}
