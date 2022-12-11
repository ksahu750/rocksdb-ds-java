package com.github.ksahu750.context;

import com.github.ksahu750.RocksDBDSModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public enum Context {
  INSTANCE;

  private final Injector injector;

  Context() {
    this.injector = Guice.createInjector(new RocksDBDSModule());
  }

  public Injector getInjector() {
    return injector;
  }
}
