<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Suggestion
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
    private $Id_Sugg;

    /**
     * @var string
     * @ORM\Column(name="topic", type="string", length=30, nullable=false)
     * @Assert\Length(
     *      min = 5,
     *      max = 50,
     *      minMessage = "topic must include at least {{ limit }} characters",
     *      maxMessage = "topic must include at most {{ limit }} characters"
     * )
     */
    private $topic;

    /**
     * @var string
     * @ORM\Column(name="description", type="string", length=30, nullable=false)
     * @Assert\Length(
     *      min = 5,
     *      max = 255,
     *      minMessage = "description must include at least {{ limit }} characters",
     *      maxMessage = "description must include at most {{ limit }} characters"
     * )
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
    private $StudentId;

    public function getId_Sugg(): ?int
    {
        return $this->Id_Sugg;
    }

    public function gettopic(): ?string
    {
        return $this->topic;
    }

    public function settopic(string $topic): self
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

    public function getStudentId(): ?int
    {
        return $this->StudentId;
    }

    public function setStudentId(int $StudentId): self
    {
        $this->StudentId = $StudentId;

        return $this;
    }


}
