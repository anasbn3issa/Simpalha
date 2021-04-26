<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Meet
 *
 * @ORM\Table(name="meet", indexes={@ORM\Index(name="fk_meet_helper_id", columns={"id_helper"}), @ORM\Index(name="fk_meet_feedback_id", columns={"feedback_id"}), @ORM\Index(name="fk_meet_student_id", columns={"id_student"}), @ORM\Index(name="fk_meet_disponibilite_id", columns={"disponibilite_id"})})
 * @ORM\Entity
 */
class Meet
{
    /**
     * @var string
     *
     * @ORM\Column(name="id", type="string", length=60, nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="specialite", type="string", length=30, nullable=false)
     */
    private $specialite;

    /**
     * @var int
     *
     * @ORM\Column(name="etat", type="integer", nullable=false)
     */
    private $etat = '0';

    /**
     * @var \Disponibilite
     *
     * @ORM\ManyToOne(targetEntity="Disponibilite")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="disponibilite_id", referencedColumnName="id")
     * })
     */
    private $disponibilite;

    /**
     * @var \Feedback
     *
     * @ORM\ManyToOne(targetEntity="Feedback")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="feedback_id", referencedColumnName="id")
     * })
     */
    private $feedback;

    /**
     * @var \Users
     *
     * @ORM\ManyToOne(targetEntity="Users")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_helper", referencedColumnName="Id")
     * })
     */
    private $idHelper;

    /**
     * @var \Users
     *
     * @ORM\ManyToOne(targetEntity="Users")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_student", referencedColumnName="Id")
     * })
     */
    private $idStudent;

    public function getId(): ?string
    {
        return $this->id;
    }

    public function getSpecialite(): ?string
    {
        return $this->specialite;
    }

    public function setSpecialite(string $specialite): self
    {
        $this->specialite = $specialite;

        return $this;
    }

    public function getEtat(): ?int
    {
        return $this->etat;
    }

    public function setEtat(int $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

    public function getDisponibilite(): ?Disponibilite
    {
        return $this->disponibilite;
    }

    public function setDisponibilite(?Disponibilite $disponibilite): self
    {
        $this->disponibilite = $disponibilite;

        return $this;
    }

    public function getFeedback(): ?Feedback
    {
        return $this->feedback;
    }

    public function setFeedback(?Feedback $feedback): self
    {
        $this->feedback = $feedback;

        return $this;
    }

    public function getIdHelper(): ?Users
    {
        return $this->idHelper;
    }

    public function setIdHelper(?Users $idHelper): self
    {
        $this->idHelper = $idHelper;

        return $this;
    }

    public function getIdStudent(): ?Users
    {
        return $this->idStudent;
    }

    public function setIdStudent(?Users $idStudent): self
    {
        $this->idStudent = $idStudent;

        return $this;
    }


}
