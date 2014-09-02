package org.enderstone.server.packet.play;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import org.enderstone.server.Location;
import org.enderstone.server.Main;
import org.enderstone.server.packet.NetworkManager;
import org.enderstone.server.packet.Packet;

public class PacketInPlayerPositionLook extends Packet {

	private double x;
	private double feetY;
	private double z;
	private float yaw;
	private float pitch;
	private boolean onGround;
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		this.x = buf.readDouble();
		this.feetY = buf.readDouble();
		this.z = buf.readDouble();
		this.yaw = buf.readFloat();
		this.pitch = buf.readFloat();
		this.onGround = buf.readBoolean();
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		throw new RuntimeException("Packet " + this.getClass().getSimpleName() + " with ID 0x" + Integer.toHexString(getId()) + " cannot be written.");
	}

	@Override
	public int getSize() throws IOException {
		return (getDoubleSize() * 3) + (getFloatSize() * 2) + 1 + getVarIntSize(getId());
	}

	@Override
	public byte getId() {
		return 0x06;
	}

	@Override
	public void onRecieve(final NetworkManager networkManager) {
		Main.getInstance().sendToMainThread(new Runnable() {

			@Override
			public void run() {
				Location loc = networkManager.player.getLocation();
				if (networkManager.player.waitingForValidMoveAfterTeleport > 0) {
					if (Math.max(Math.max(getX() < loc.getX() ? loc.getX() - getX() : getX() - loc.getX(), getHeadY() < loc.getY() ? loc.getY() - getHeadY() : getHeadY() - loc.getY()), getZ() < loc.getZ() ? loc.getZ() - getZ() : getZ() - loc.getZ()) > 0.1) {
						if (networkManager.player.waitingForValidMoveAfterTeleport++ > 100) {
							networkManager.player.teleport(loc);
						}
						return;
					}
					networkManager.player.waitingForValidMoveAfterTeleport = 0;
				}
				networkManager.player.broadcastLocation(new Location("", getX(), getFeetY(), getZ(), getYaw(), getPitch()));
				networkManager.player.broadcastRotation(getPitch(), getYaw());
				loc.setX(getX());
				loc.setY(getFeetY());
				loc.setZ(getZ());
				loc.setPitch(getPitch());
				loc.setYaw(getYaw());
				networkManager.player.setOnGround(isOnGround());
			}
		});
	}

	public double getX() {
		return x;
	}

	public double getFeetY() {
		return feetY;
	}

	public double getHeadY() {
		return getFeetY() + 1.62;
	}

	public double getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public boolean isOnGround() {
		return onGround;
	}
}
