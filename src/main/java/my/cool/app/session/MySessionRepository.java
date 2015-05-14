package my.cool.app.session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component("sessionRepository")
public class MySessionRepository extends MapSessionRepository{
	private static final Log logger = LogFactory.getLog(MySessionRepository.class);

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Override
	public ExpiringSession createSession() {
		System.out.println("createSession");
		return super.createSession();
	}

	@Override
	public void delete(String sessionId) {
		System.out.println("delete");
		super.delete(sessionId);
		publishEvent(new SessionDestroyedEvent(this, sessionId));
	}

	private void publishEvent(ApplicationEvent event) {
		try {
			this.eventPublisher.publishEvent(event);
		}
		catch (Throwable ex) {
			logger.error("Error publishing " + event + ".", ex);
		}
	}

	@Override
	public ExpiringSession getSession(String sessionId) {
		System.out.println("getSession");
		return super.getSession(sessionId);
	}

	@Override
	public void save(ExpiringSession session) {
		System.out.println("save");
		super.save(session);
	}

}
