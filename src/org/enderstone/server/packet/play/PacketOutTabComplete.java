package org.enderstone.server.packet.play;

import java.util.List;
import org.enderstone.server.packet.Packet;
import io.netty.buffer.ByteBuf;

/**
 *
 * @author Fernando
 */
public class PacketOutTabComplete extends Packet {

	private int count;
	private List<String> newCommand;

	public PacketOutTabComplete(List<String> newCommand) {
		this.count = newCommand.size();
		this.newCommand = newCommand;
	}

	public PacketOutTabComplete() {
	}

	@Override
	public void read(ByteBuf buf) throws Exception {
		throw new RuntimeException("Packet " + this.getClass().getSimpleName() + " with ID 0x" + Integer.toHexString(getId()) + " cannot be read.");
	}

	@Override
	public void write(ByteBuf buf) throws Exception {
		writeVarInt(count, buf);
		for (String l : newCommand)
			writeString(l, buf);
	}

	@Override
	public int getSize() throws Exception {
		int size = 0;
		size += getVarIntSize(count);
		for (String l : newCommand)
			size += getStringSize(l);
		return size + getVarIntSize(getId());
	}

	@Override
	public byte getId() {
		return 0x3A;
	}
	
	
}
