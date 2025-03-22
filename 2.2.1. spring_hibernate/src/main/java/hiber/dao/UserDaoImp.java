package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public void add(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    public List<User> listCars() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from Car");
        return query.getResultList();
    }

    @Override
    public List<User> getUser(String model, int series) {
        String hql = "from User u where u.userCar.model = :model and u.userCar.series = :series";
        List<User> user = sessionFactory.getCurrentSession().createQuery(hql, User.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .getResultList();
        if (!user.isEmpty()) {
            return user;
        } else {
            return null;
        }
    }
}
