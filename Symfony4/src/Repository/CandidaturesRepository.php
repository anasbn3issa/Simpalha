<?php

namespace App\Repository;


use App\Entity\Candidatures;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\NonUniqueResultException;
use Doctrine\ORM\NoResultException;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Candidatures|null find($id, $lockMode = null, $lockVersion = null)
 * @method Candidatures|null findOneBy(array $criteria, array $orderBy = null)
 * @method Candidatures[]    findAll()
 * @method Candidatures[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class CandidaturesRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Candidatures::class);
    }


    /**
     * @return Candidatures[] Returns an array of User objects
     */
    public function findByRoleField($value): array
    {
        return $this->createQueryBuilder('u')
            ->andWhere('u.role = :val')
            ->setParameter('val', $value)
            ->orderBy('u.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
            ;
    }
    public function findEntitiesByString($str)
    {
        return $this->getEntityManager()
            ->createQuery('SELECT c FROM App:Candidatures c  WHERE c.description LIKE :str'

            )->setParameter('str', '%'.$str.'%')->getResult();
    }








}
