<?php

namespace App\Repository;

use App\Entity\Meet;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Meet|null find($id, $lockMode = null, $lockVersion = null)
 * @method Meet|null findOneBy(array $criteria, array $orderBy = null)
 * @method Meet[]    findAll()
 * @method Meet[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class MeetRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Meet::class);
    }

     /**
      * @return Meet[] Returns an array of Meet objects
      */

    public function search($value)
    {
        $qb = $this->createQueryBuilder('m');
        return $qb
            ->join("m.idHelper", "helper")
            ->join("m.idStudent", "student")
            ->orWhere($qb->expr()->like('m.specialite', $qb->expr()->literal('%' . $value . '%')))
            ->orWhere($qb->expr()->like('helper.username', $qb->expr()->literal('%' . $value . '%')))
            ->orWhere($qb->expr()->like('student.username', $qb->expr()->literal('%' . $value . '%')))
            ->getQuery()
            ->getResult()
        ;
    }


    // /**
    //  * @return Meet[] Returns an array of Meet objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('m')
            ->andWhere('m.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('m.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Meet
    {
        return $this->createQueryBuilder('m')
            ->andWhere('m.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */


}
