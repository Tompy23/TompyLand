package net.tompy.tl.serialization;

import net.tompy.tl.data.CharacterSerialize;
import net.tompy.tl.data.FeatureSerialize;
import net.tompy.tl.data.HexSerialize;
import net.tompy.tl.data.MapSerialize;
import net.tompy.tl.data.SimpleColor;
import net.tompy.tl.data.TerrainSerialize;
import net.tompy.tl.packet.Packet;
import net.tompy.tl.packet.PacketClientConnect;
import net.tompy.tl.packet.PacketMapCreatorConnect;
import net.tompy.tl.packet.PacketMapCreatorIOPayload;
import net.tompy.tl.packet.PacketMapCreatorLoadList;
import net.tompy.tl.packet.PacketMapCreatorLoadRequest;
import net.tompy.tl.packet.PacketMapCreatorRequestIOStatus;
import net.tompy.tl.packet.PacketRequestCharacterCreation;
import net.tompy.tl.packet.PacketStartCharacterGame;
import net.tompy.tl.packet.PacketSubmitNewCharacter;
import net.tompy.tl.packet.PacketUserCreationComplete;
import net.tompy.tl.packet.PacketUserSendMessage;
import net.tompy.tl.packet.PacketUserStartGame;

import com.esotericsoftware.kryo.Kryo;

public class SerialRegistration 
{
	public static void register( Kryo k )
	{
		k.register( Packet.class );
		k.register( PacketClientConnect.class );
		k.register( PacketRequestCharacterCreation.class );
		k.register( PacketStartCharacterGame.class );
		k.register( PacketSubmitNewCharacter.class );
		k.register( PacketUserCreationComplete.class );
		k.register( PacketUserStartGame.class );
		k.register( PacketUserSendMessage.class );

		k.register( PacketMapCreatorConnect.class );
		k.register( PacketMapCreatorIOPayload.class );
		k.register( PacketMapCreatorLoadRequest.class );
		k.register( PacketMapCreatorLoadList.class );
		k.register( PacketMapCreatorRequestIOStatus.class );

		k.register( CharacterSerialize.class );
		
		k.register( MapSerialize.class );
		k.register( HexSerialize[].class );
		k.register( FeatureSerialize[].class );
		k.register( HexSerialize.class );
		k.register( FeatureSerialize.class );
		k.register( TerrainSerialize.class );

		k.register( SimpleColor.class );
		k.register( float[].class );
		k.register( String[].class );
	}
}
