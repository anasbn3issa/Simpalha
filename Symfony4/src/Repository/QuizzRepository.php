<?php

namespace App\Repository;

use App\Entity\Quizz;
use App\Entity\Users;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\QueryBuilder;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Quizz|null find($id, $lockMode = null, $lockVersion = null)
 * @method Quizz|null findOneBy(array $criteria, array $orderBy = null)
 * @method Quizz[]    findAll()
 * @method Quizz[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class QuizzRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Quizz::class);
    }



    public function getWithSearchQueryBuilder(?string $term): QueryBuilder
    {
        $qb = $this->createQueryBuilder('c');

        if($term) {
            $qb->andWhere('c.subject LIKE :term OR c.title LIKE :term')
                ->setParameter('term', '%'.$term.'%')
            ;
        }

        return $qb
            ->orderBy('c.subject', 'DESC');
    }



    public function getHelperQuizListQueryBuilder(?Users $user,?string $term): QueryBuilder
    {
        $qb = $this->createQueryBuilder('c');

        if($term) {
            $qb->andWhere('c.helper = :helper')
                ->setParameter('helper',$user)
                ->andWhere('c.subject LIKE :term OR c.title LIKE :term')
                ->setParameter('term', '%'.$term.'%')
            ;
        }

        return $qb
            ->orderBy('c.subject', 'DESC');
    }

    // /**
    //  * @return Quizz[] Returns an array of Quizz objects
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
    public function findOneBySomeField($value): ?Quizz
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
