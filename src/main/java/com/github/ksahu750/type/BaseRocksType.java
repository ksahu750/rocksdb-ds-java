package com.github.ksahu750.type;

import com.github.ksahu750.context.Context;
import com.github.ksahu750.provider.RocksDBWrapper;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public abstract class BaseRocksType {
  public static String DELIMITER = ":";

  @Getter protected DataType dataType;

  protected BaseRocksType(DataType type) {
    this.dataType = type;
  }

  protected BaseRocksType() {}

  protected static RocksDBWrapper dbWrapper() {
    return Context.INSTANCE.getInjector().getInstance(RocksDBWrapper.class);
  }

  public enum DataType {
    LIST,
  }
}
