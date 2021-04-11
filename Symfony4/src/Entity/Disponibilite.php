<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Disponibilite
 *
 * @ORM\Table(name="disponibilite", indexes={@ORM\Index(name="fk_disponibilite_helper_id", columns={"helperId"})})
 * @ORM\Entity
 */
class Disponibilite
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
     * @ORM\Column(name="dateDeb", type="string", length=255, nullable=false)
     */
    private $datedeb;

    /**
     * @var string
     *
     * @ORM\Column(name="dateFin", type="string", length=255, nullable=false)
     */
    private $datefin;

    /**
     * @var int
     *
     * @ORM\Column(name="etat", type="integer", nullable=false)
     */
    private $etat = '0';

    /**
     * @var \Users
     *
     * @ORM\ManyToOne(targetEntity="Users")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="helperId", referencedColumnName="Id")
     * })
     */
    private $helperid;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDatedeb(): ?string
    {
        return $this->datedeb;
    }

    public function setDatedeb(string $datedeb): self
    {
        $this->datedeb = $datedeb;

        return $this;
    }

    public function getDatefin(): ?string
    {
        return $this->datefin;
    }

    public function setDatefin(string $datefin): self
    {
        $this->datefin = $datefin;

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

    public function getHelperid(): ?Users
    {
        return $this->helperid;
    }

    public function setHelperid(?Users $helperid): self
    {
        $this->helperid = $helperid;

        return $this;
    }


}
