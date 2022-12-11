package com.github.ksahu750.type.list;

import com.github.ksahu750.type.PersistedBaseRocksType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;

class ListMetadata extends PersistedBaseRocksType implements Serializable {

  @Serial private static final long serialVersionUID = -8873104585658422050L;
  int headIdx = 0;

  int tailIdx = 0;

  public ListMetadata() {
    super(DataType.LIST, PersistedDataType.LIST_METADATA);
  }

  /**
   * Query metadata and obtain current size
   *
   * @return size of list
   */
  public int size() {
    return tailIdx - headIdx;
  }

  // maybe an optimization
  @Serial
  private void readObject(ObjectInputStream is) throws ClassNotFoundException, IOException {
    dataType = DataType.LIST;
    persistedType = PersistedDataType.LIST_METADATA;
    headIdx = is.readInt();
    tailIdx = is.readInt();
  }

  @Serial
  private void writeObject(ObjectOutputStream os) throws IOException {
    os.writeInt(headIdx);
    os.writeInt(tailIdx);
  }
}
