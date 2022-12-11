package com.github.ksahu750.provider;

import com.github.ksahu750.configuration.RocksDBConfiguration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

@Slf4j
public class RocksDBProvider implements Provider<RocksDB> {

  @Inject private RocksDBConfiguration configuration;

  @Override
  public RocksDB get() {
    try (final Options options = new Options().setCreateIfMissing(true)) {
      return RocksDB.open(options, configuration.getPath());
    } catch (RocksDBException e) {
      log.error("Unable to initialize rocksDB", e);
      throw new RuntimeException("Unable to initialize rocksDB", e);
    }
  }
}
