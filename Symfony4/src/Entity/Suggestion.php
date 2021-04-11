<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Suggestion
 *
 * @ORM\Table(name="suggestion", indexes={@ORM\Index(name="fk_suggestion_studentId", columns={"StudentId"})})
 * @ORM\Entity
 */
class Suggestion
{
    /**
     * @var int
     *
     * @ORM\Column(name="Id_Sugg", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idSugg;

    /**
     * @var string
     *
     * @ORM\Column(name="topic", type="string", length=30, nullable=false)
     */
    private $topic;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="string", length=30, nullable=false)
     */
    private $description;

    /**
     * @var string|null
     *
     * @ORM\Column(name="Record", type="string", length=255, nullable=true)
     */
    private $record;

    /**
     * @var int
     *
     * @ORM\Column(name="StudentId", type="integer", nullable=false)
     */
    private $studentid;

    public function getIdSugg(): ?int
    {
        return $this->idSugg;
    }

    public function getTopic(): ?string
    {
        return $this->topic;
    }

    public function setTopic(string $topic): self
    {
        $this->topic = $topic;

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

    public function getRecord(): ?string
    {
        return $this->record;
    }

    public function setRecord(?string $record): self
    {
        $this->record = $record;

        return $this;
    }

    public function getStudentid(): ?int
    {
        return $this->studentid;
    }

    public function setStudentid(int $studentid): self
    {
        $this->studentid = $studentid;

        return $this;
    }


}
