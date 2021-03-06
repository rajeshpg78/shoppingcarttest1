package com.niit.shoppingcart.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.shoppingcart.model.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<User> list() {
		@SuppressWarnings("unchecked")
		List<User> listUser = (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return listUser;

	}

	@Transactional
	public User get(String id) {
		String hql = "from User where id=" + "'" + id + "'";
		Query query = (Query) sessionFactory.getCurrentSession().createQuery(hql);
		List<User> listUser = (List<User>) query.list();

		if (listUser != null && !listUser.isEmpty()) {
			return listUser.get(0);
		}
		return null;
	}

	@Transactional
	public void saveOrUpdate(User user) {
		System.out.println("save or upadte");
		sessionFactory.getCurrentSession().saveOrUpdate(user);
		System.out.println("user is saved***");

	}

	@Transactional
	public void delete(String id) {
		sessionFactory.getCurrentSession().delete(id);

	}
	@Transactional
	public boolean isValidUser(String id, String password) {
		String hql = "from User where id='"+id+"' and "+" password='"+password+"'";
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		List list = q.list();
		if (list == null || list.isEmpty()) {
			return false;
		}
		return true;
	}

}
