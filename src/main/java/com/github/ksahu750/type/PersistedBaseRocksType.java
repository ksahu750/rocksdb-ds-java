package com.github.ksahu750.type;

import lombok.Getter;

public abstract class PersistedBaseRocksType extends BaseRocksType {

  @Getter protected PersistedDataType persistedType;

  protected PersistedBaseRocksType(DataType type, PersistedDataType persistedType) {
    super(type);
    this.persistedType = persistedType;
  }

  protected PersistedBaseRocksType() {}

  public enum PersistedDataType {
    LIST_METADATA,
  }
}
