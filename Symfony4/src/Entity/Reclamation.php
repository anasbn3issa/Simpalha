<?php

namespace App\Entity;
use App\Repository\ReclamationRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


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
     * @Assert\Length(
     *      min = 5,
     *      max = 255,
     *      minMessage = "description must include at least {{ limit }} characters",
     *      maxMessage = "description must include at most {{ limit }} characters"
     * )
     */
    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="FileSelected", type="string", length=255, nullable=false)
     */
    private $fileselected;

    /**
     * @var string
     *
     * @ORM\Column(name="Record", type="string", length=255, nullable=false)
     */
    private $record;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateRec", type="datetime", nullable=true)
     */
    public $daterec;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateResolution", type="date", nullable=true)
     */
    public $dateresolution;

    public function __construct()
    {
        $this->setDaterec(new \DateTime());

    }
    /**
     * @ORM\PostLoad()
     */

    public function PostLoad()
    {
        $this->updateAt = new \DateTime();
    }
     /**
     * @var int
     *
     * @ORM\Column(name="ValidStudent", type="integer", nullable=false)
     */
    private $validstudent;

    /**
     * @var int|null
     *
     * @ORM\Column(name="Status", type="integer", nullable=true)
     */
    private $status;

    /**
     * @var int
     *
     * @ORM\Column(name="ValidHelper", type="integer", nullable=false)
     */
    private $validhelper;


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

    public function getFileselected(): ?string
    {
        return $this->fileselected;
    }

    public function setFileselected(string $fileselected): self
    {
        $this->fileselected = $fileselected;

        return $this;
    }

    public function getRecord(): ?string
    {
        return $this->record;
    }

    public function setRecord(string $record): self
    {
        $this->record = $record;

        return $this;
    }

    public function getDaterec()
    {
        return $this->daterec;
    }

    public function setDaterec($daterec)
    {
        $this->daterec = $daterec;

        return $this;
    }

    public function getDateresolution()
    {
        return $this->dateresolution;
    }

    public function setDateresolution($dateresolution)
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

    public function getStatus(): ?int
    {
        return $this->status;
    }

    public function setStatus(?int $status): self
    {
        $this->status = $status;

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




}
