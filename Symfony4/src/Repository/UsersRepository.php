<?php

namespace App\Repository;


use App\Entity\Users;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\NonUniqueResultException;
use Doctrine\ORM\NoResultException;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Users|null find($id, $lockMode = null, $lockVersion = null)
 * @method Users|null findOneBy(array $criteria, array $orderBy = null)
 * @method Users[]    findAll()
 * @method Users[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class UsersRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Users::class);
    }


    /**
     * @return Users[] Returns an array of User objects
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

    /**
     * @return Users[] Returns an array of User objects
     */
    public function findHelpers(): array
    {
        $qb = $this->createQueryBuilder('u');

        return $qb
            ->orwhere($qb->expr()->neq('u.specialite', "''"))
            ->getQuery()
            ->getResult()
            ;
    }








}
