package io.spring.backoffice_batch.job;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import io.spring.main.model.order.IfItem;
import io.spring.main.model.order.entity.IfOrderDetail;
import io.spring.main.model.order.entity.IfOrderMaster;



public class JpaCustomItemWriter1 extends JpaItemWriter<IfItem> {



	private EntityManagerFactory entityManagerFactory;
	private boolean usePersist = false;

	public JpaCustomItemWriter1(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	};

	@Override
	public void write(List<? extends IfItem> items) {

		EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
		if (entityManager == null) {
			throw new DataAccessResourceFailureException("Unable to obtain a transactional EntityManager");
		}

		int cnt = 0;

		for (IfItem o : items) {

			System.out.println(cnt);

			cnt = cnt + 1;



			if (entityManager.getTransaction().isActive() == false) {
				entityManager.getTransaction().begin();
			}

			// entityManager.getTransaction().begin();
			System.out.println(o.getIom().getChannelOrderNo());

			// IfItem o = (IfItem) items.get(0);
			this.saveMaster(entityManager, o.getIom());
			this.saveDetail(entityManager, o.getIods());
			entityManager.flush();
			entityManager.getTransaction().commit();

			// entityManager.flush();

			// TODO Auto-generated method stub
			// super.write(items);



		}


	}

	private void saveMaster(EntityManager entityManager, IfOrderMaster item) {


		if (item != null) {

				if (!entityManager.contains(item)) {
					if (usePersist) {
						entityManager.persist(item);
					} else {
						entityManager.merge(item);
					}

				}

		}

	}

	protected void saveDetail(EntityManager entityManager, List<IfOrderDetail> items) {
		System.out.println("****************************************************");
		System.out.println(items);
		System.out.println("****************************************************");

		if (!items.isEmpty()) {
			for (IfOrderDetail item : items) {



				if (!entityManager.contains(item)) {
					if (usePersist) {
						entityManager.persist(item);
					} else {
						entityManager.merge(item);
					}

				}
			}

		}

	}
}
