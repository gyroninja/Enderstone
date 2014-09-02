package org.enderstone.server.packet.play;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.UUID;
import org.enderstone.server.entity.DataWatcher;
import org.enderstone.server.packet.Packet;

public class PacketOutSpawnPlayer extends Packet {

	private int entityId;
	private UUID uuid;
	private int x;
	private int y;
	private int z;
	private byte yaw;
	private byte pitch;
	private short currentSlot;
	private DataWatcher dataWatcher;
	
	public PacketOutSpawnPlayer(int entityId, UUID uuid, int x, int y, int z, byte yaw, byte pitch, short currentSlot, DataWatcher dataWatcher) {
		this.entityId = entityId;
		this.uuid = uuid;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.currentSlot = currentSlot;
		this.dataWatcher = dataWatcher;
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		throw new RuntimeException("Packet " + this.getClass().getSimpleName() + " with ID 0x" + Integer.toHexString(getId()) + " cannot be read.");
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		writeVarInt(entityId, buf);
		writeUUID(uuid, buf);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeByte(yaw);
		buf.writeByte(pitch);
		buf.writeShort(currentSlot);
		writeDataWatcher(dataWatcher, buf);
	}

	@Override
	public int getSize() throws IOException {
		return getVarIntSize(entityId) + getUUIDSize() + (getIntSize() * 3) + 2 + getShortSize() + getDataWatcherSize(dataWatcher);
	}

	@Override
	public byte getId() {
		return 0x0C;
	}
}
