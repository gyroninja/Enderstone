package org.enderstone.server.packet.play;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import org.enderstone.server.packet.Packet;

public class PacketOutPlayerPositionLook extends Packet {

	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	private byte flags;

	public PacketOutPlayerPositionLook(double x, double y, double z, float yaw, float pitch, byte flags) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.flags = flags;
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		throw new RuntimeException("Packet " + this.getClass().getSimpleName() + " with ID 0x" + Integer.toHexString(getId()) + " cannot be read.");
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeFloat(yaw);
		buf.writeFloat(pitch);
		buf.writeByte(flags);
	}

	@Override
	public int getSize() throws IOException {
		return (getDoubleSize() * 3) + (getFloatSize() * 2) + 1 + getVarIntSize(getId());
	}

	@Override
	public byte getId() {
		return 0x08;
	}

}
