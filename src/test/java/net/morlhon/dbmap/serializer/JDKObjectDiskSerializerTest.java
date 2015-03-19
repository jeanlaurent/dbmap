package net.morlhon.dbmap.serializer;

import net.morlhon.dbmap.DiskSerializer;

public class JDKObjectDiskSerializerTest  extends AbstractDiskSerializerTest {

    @Override
    protected DiskSerializer buildSerializer() {
        return new JDKObjectDiskSerializer();
    }
}