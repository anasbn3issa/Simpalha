<?php

namespace App\Repository;

use App\Entity\QuizzResult;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method QuizzResult|null find($id, $lockMode = null, $lockVersion = null)
 * @method QuizzResult|null findOneBy(array $criteria, array $orderBy = null)
 * @method QuizzResult[]    findAll()
 * @method QuizzResult[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class QuizzResultRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, QuizzResult::class);
    }

    // /**
    //  * @return QuizzResult[] Returns an array of QuizzResult objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('q')
            ->andWhere('q.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('q.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?QuizzResult
    {
        return $this->createQueryBuilder('q')
            ->andWhere('q.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
