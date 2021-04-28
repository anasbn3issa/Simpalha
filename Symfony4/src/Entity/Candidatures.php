<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Candidatures
 *
 * @ORM\Table(name="candidatures")
 * @ORM\Entity(repositoryClass="App\Repository\CandidaturesRepository")
 */
class Candidatures
{
    /**
     * @var int
     *
     * @ORM\Column(name="idc", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idc;



    /**
     * @var string
     *
     * @ORM\Column(name="fichier", type="string", length=255, nullable=false)
     */
    private $fichier;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="string", length=255, nullable=false)
     */
    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="Specialty", type="string", length=255, nullable=true)
     */
    private $Specialty;


    /**
     * @var int|null
     *
     * @ORM\Column(name="status", type="integer", nullable=true)
     */
    private $status;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="datec", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $datec = 'CURRENT_TIMESTAMP';

    /**
     * @ORM\OneToOne(targetEntity="App\Entity\Users")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="user_id", referencedColumnName="Id",nullable=false)
     * })
     */
    private $createdBy;

    /**
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * @param string $description
     */
    public function setDescription(string $description)
    {
        $this->description = $description;
    }

    /**
     * @return string
     */
    public function getSpecialty()
    {
        return $this->Specialty;
    }

    /**
     * @param string $Specialty
     */
    public function setSpecialty(string $Specialty)
    {
        $this->Specialty = $Specialty;
    }

    /**
     * @return mixed
     */
    public function getCreatedBy()
    {
        return $this->createdBy;
    }

    /**
     * @param mixed $createdBy
     */
    public function setCreatedBy($createdBy)
    {
        $this->createdBy = $createdBy;
    }

    public function getIdc(): ?int
    {
        return $this->idc;
    }

    public function getFichier(): ?string
    {
        return $this->fichier;
    }

    public function setFichier(string $fichier): self
    {
        $this->fichier = $fichier;

        return $this;
    }

    public function getStatus(): ?int
    {
        return $this->status;
    }

    public function setStatus(?int $status): self
    {
        $this->status = $status;

        return $this;
    }


    public function getDatec(): ?\DateTimeInterface
    {
        return $this->datec;
    }

    public function setDatec(\DateTimeInterface $datec): self
    {
        $this->datec = $datec;

        return $this;
    }


}
