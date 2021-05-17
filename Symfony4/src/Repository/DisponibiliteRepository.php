<?php

namespace App\Repository;

use App\Entity\Disponibilite;
use App\Entity\Users;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Disponibilite|null find($id, $lockMode = null, $lockVersion = null)
 * @method Disponibilite|null findOneBy(array $criteria, array $orderBy = null)
 * @method Disponibilite[]    findAll()
 * @method Disponibilite[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class DisponibiliteRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Disponibilite::class);
    }

    public function getHelperDisp(Disponibilite $d, Users  $helper)
    {
        $qb = $this->createQueryBuilder('d');
        if($d != null){
            $qb->where('d = :dis')
                ->orWhere('d.etat = 0 AND d.helperid = :helper')
                ->setParameter('dis', $d)
                ->setParameter('helper', $helper);
        }

        else

            return $qb;
    }

    public function getHelperDispMeet(Users  $helper)
    {
        return $this->createQueryBuilder('d')
            ->where('d.helperid = :helper')
            ->setParameter('helper', $helper)
            ->getQuery()
            ->getResult();
    }

    public function getDisp(Users  $helper)
    {
        $qb = $this->createQueryBuilder('d')
            ->where('d.etat = 0 AND d.helperid = :helper')
            ->setParameter('helper', $helper);

        return $qb;
    }

    // /**
    //  * @return Disponibilite[] Returns an array of Disponibilite objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('d')
            ->andWhere('d.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('d.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Disponibilite
    {
        return $this->createQueryBuilder('d')
            ->andWhere('d.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
