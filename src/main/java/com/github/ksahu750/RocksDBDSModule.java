package com.github.ksahu750;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ksahu750.provider.RocksDBProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import java.io.IOException;
import java.util.Properties;
import org.rocksdb.RocksDB;

public class RocksDBDSModule extends AbstractModule {

  @Provides
  public ObjectMapper mapper() {
    return new ObjectMapper();
  }

  @Override
  protected void configure() {
    // Bind Application Properties
    try {
      Properties properties = new Properties();
      properties.load(
          this.getClass().getClassLoader().getResourceAsStream("application.properties"));
      Names.bindProperties(binder(), properties);
    } catch (IOException ex) {
      throw new IllegalArgumentException("Failed to read file", ex);
    }

    // Bind rocksdb instance
    bind(RocksDB.class).toProvider(RocksDBProvider.class).in(Scopes.SINGLETON);

    /*bind(RocksDBWrapper.class).to(RocksDBWrapper.class);*/
  }
}
