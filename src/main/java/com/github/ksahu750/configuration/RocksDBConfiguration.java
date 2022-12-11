package com.github.ksahu750.configuration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import lombok.Getter;

@Singleton
@Getter
public class RocksDBConfiguration {

  @Inject
  @Named("rocksdb.path")
  private String path;
}
