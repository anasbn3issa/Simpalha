<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Reclamation
 *
 * @ORM\Table(name="reclamation", indexes={@ORM\Index(name="fk_reclamation_studentId", columns={"StudentId"})})
 * @ORM\Entity
 */
class Reclamation
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="Idreportee", type="string", length=8, nullable=false)
     */
    private $idreportee;

    /**
     * @var string
     *
     * @ORM\Column(name="Idreported", type="string", length=8, nullable=false)
     */
    private $idreported;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="string", length=255, nullable=false)
     */
    private $description;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateRec", type="date", nullable=false)
     */
    private $daterec;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateResolution", type="date", nullable=false)
     */
    private $dateresolution;

    /**
     * @var int
     *
     * @ORM\Column(name="ValidStudent", type="integer", nullable=false)
     */
    private $validstudent;

    /**
     * @var int
     *
     * @ORM\Column(name="ValidHelper", type="integer", nullable=false)
     */
    private $validhelper;

    /**
     * @var \Users
     *
     * @ORM\ManyToOne(targetEntity="Users")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="StudentId", referencedColumnName="Id")
     * })
     */
    private $studentid;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdreportee(): ?string
    {
        return $this->idreportee;
    }

    public function setIdreportee(string $idreportee): self
    {
        $this->idreportee = $idreportee;

        return $this;
    }

    public function getIdreported(): ?string
    {
        return $this->idreported;
    }

    public function setIdreported(string $idreported): self
    {
        $this->idreported = $idreported;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getDaterec(): ?\DateTimeInterface
    {
        return $this->daterec;
    }

    public function setDaterec(\DateTimeInterface $daterec): self
    {
        $this->daterec = $daterec;

        return $this;
    }

    public function getDateresolution(): ?\DateTimeInterface
    {
        return $this->dateresolution;
    }

    public function setDateresolution(\DateTimeInterface $dateresolution): self
    {
        $this->dateresolution = $dateresolution;

        return $this;
    }

    public function getValidstudent(): ?int
    {
        return $this->validstudent;
    }

    public function setValidstudent(int $validstudent): self
    {
        $this->validstudent = $validstudent;

        return $this;
    }

    public function getValidhelper(): ?int
    {
        return $this->validhelper;
    }

    public function setValidhelper(int $validhelper): self
    {
        $this->validhelper = $validhelper;

        return $this;
    }

    public function getStudentid(): ?Users
    {
        return $this->studentid;
    }

    public function setStudentid(?Users $studentid): self
    {
        $this->studentid = $studentid;

        return $this;
    }


}
