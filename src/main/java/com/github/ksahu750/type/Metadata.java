package com.github.ksahu750.type;

import lombok.Getter;

public abstract class Metadata {

  @Getter protected Type type;

  protected Metadata(Type type) {
    this.type = type;
  }

  protected Metadata() {}

  public enum Type {
    LIST_METADATA,
  }
}
