package br.com.gov.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.gov.model.Usuario;
import br.com.gov.util.cdi.jpa.Transactional;

public class RepositorioUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void CadastrarUsuario(Usuario usuario) {

		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		manager.merge(usuario);
		trx.commit();

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(Usuario usuario) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);

		if (StringUtils.isNotBlank(usuario.getNomeCandidato())) {
			criteria.add(Restrictions.ilike("NomeCandidato",
					usuario.getNomeCandidato(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("NomeCandidato")).list();

	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listaVaoFazerProva() {
		
		String pago = "Confirmado";
		String isento =  "Isento";
			Session session = manager.unwrap(Session.class);
			Criteria criteria = session.createCriteria(Usuario.class);
			
			if (StringUtils.isNotBlank(pago)) {
				criteria.add(Restrictions.ilike("situacao", pago));
			}
						
			if (StringUtils.isNotBlank(isento)) {
				criteria.add(Restrictions.ilike("situacao", isento));
			}
			
			return criteria.addOrder(Order.asc("cargo")).list();
		
		
	}

	public List<Usuario> todos() {
		try {
			return manager.createQuery("from Usuario ", Usuario.class)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public Usuario porEmail(String email) {
		try {
			return manager
					.createQuery("from Usuario where upper(email) = :email",
							Usuario.class)
					.setParameter("email", email.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario porCpf(String cpf) {
		try {
			return manager
					.createQuery("from Usuario where upper(cpf) = :cpf",
							Usuario.class)
					.setParameter("cpf", cpf.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	public List<Usuario> porPagamentoSituacao(String situacao) {
		try {
			return manager
					.createQuery("from Usuario where upper(situacao) = :situacao",
							Usuario.class)
					.setParameter("situacao", situacao.toUpperCase())
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	@Transactional
	public void remover(Usuario usuario) {

		EntityTransaction trx = manager.getTransaction();
		trx.begin();

		try {

			usuario = porId(usuario.getId());
			manager.remove(usuario);
			trx.commit();
			manager.flush();

		} catch (Exception e) {

		}

	}


	
	public List<Usuario> porCargo(String cargo) {
		try {
			return manager
					.createQuery("from Usuario where upper(cargo) = :cargo",
							Usuario.class)
					.setParameter("cargo", cargo.toUpperCase())
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
