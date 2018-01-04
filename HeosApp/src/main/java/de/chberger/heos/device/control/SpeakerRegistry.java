package de.chberger.heos.device.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import de.chberger.heos.device.Speaker;
import de.chberger.protocoll.telnet.api.TelnetClient;

@ApplicationScoped
public class SpeakerRegistry {
	
	private Map<Long,TelnetClient> registry = new HashMap<Long,TelnetClient>();
	
	public void addSpeaker(Speaker speaker, TelnetClient client) {
		if(registry.get(speaker.getPid())!=null){
			registry.put(speaker.getPid(), client);
		}
	}
	
	public void addSpeakers(Set<Speaker> speakers, TelnetClient client) {
		for (Speaker speaker : speakers) {
			addSpeaker(speaker, client);
		}
	}
	
	public TelnetClient getAssocTelnetClient(Speaker speaker) {
		return getAssocTelnetClient(speaker.getPid());
	}
	
	public TelnetClient getAssocTelnetClient(Long playerId) {
		return registry.get(playerId);
	}
	
	public List<TelnetClient> getAllTelnetClients(){
		return new ArrayList<TelnetClient>(registry.values());
	}

}
