<?php

namespace App\Repository;

use App\Entity\QuizzResult;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\DBAL\Types\Type;
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



    public function checkIfPassedQuizInLast24Hours($quizz,$studId)
    {
        $date = new \DateTime();
        $dateSub = $date->sub(new \DateInterval('P1D'));

        $qb = $this->createQueryBuilder('q');

        $qb->where('q.resultDate >= :from')
            ->setParameter('from',$dateSub)
            ->andWhere('q.quizz = :valb')
            ->setParameter('valb', $quizz)
            ->andWhere('q.studentId = :valc')
            ->setParameter('valc', $studId)
        ;

        return $qb->getQuery()->getOneOrNullResult();
    }



    public function findAllBeforeSelectedTime($quiz,$time)
    {

        $qb = $this->createQueryBuilder('q');

        $qb->where('q.resultDate < :to')
            ->setParameter('to',$time)
            ->andWhere('q.quizz = :valb')
            ->setParameter('valb', $quiz)
            ->orderBy('q.resultDate', 'DESC')
        ;

        return $qb->getQuery()->getResult();
    }



    public function getAllBeforeSelectedTimeQueryBuilder($quiz,$time)
    {

        $qb = $this->createQueryBuilder('q');

        $qb->where('q.resultDate < :to')
            ->setParameter('to',$time)
            ->andWhere('q.quizz = :valb')
            ->setParameter('valb', $quiz)
            ->orderBy('q.resultDate', 'DESC')
        ;

        return $qb;
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
