package my.cool.app.session;

import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.stereotype.Component;

@Component("sessionRepository")
public class MySessionRepository extends MapSessionRepository{

	@Override
	public ExpiringSession createSession() {
		System.out.println("createSession");
		return super.createSession();
	}

	@Override
	public void delete(String id) {
		System.out.println("delete");
		super.delete(id);
	}

	@Override
	public ExpiringSession getSession(String id) {
		System.out.println("getSession");
		return super.getSession(id);
	}

	@Override
	public void save(ExpiringSession session) {
		System.out.println("save");
		super.save(session);
	}

}
