package com.github.ksahu750.type;

import com.github.ksahu750.context.Context;
import com.github.ksahu750.wrapper.RocksDBWrapper;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public abstract class BaseRocksType {
  public static String DELIMITER = ":";

  @Getter protected Type type;

  protected BaseRocksType(Type type) {
    this.type = type;
  }

  protected BaseRocksType() {}

  protected static RocksDBWrapper dbWrapper() {
    return Context.INSTANCE.getInjector().getInstance(RocksDBWrapper.class);
  }

  public enum Type {
    LIST,
  }
}
