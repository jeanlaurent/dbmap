package net.morlhon.dbmap.serializer;

import net.morlhon.dbmap.DiskSerializer;

public class JSONDiskSerializerTest extends AbstractDiskSerializerTest {

    @Override
    protected DiskSerializer buildSerializer() {
        return new JSONDiskSerializer();
    }
}