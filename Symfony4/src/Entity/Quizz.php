<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Quizz
 *
 * @ORM\Table(name="quizz", indexes={@ORM\Index(name="fk_quizz_user_id", columns={"helper_id"})})
 * @ORM\Entity
 */
class Quizz
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
     * @ORM\Column(name="title", type="string", length=500, nullable=false)
     */
    private $title;

    /**
     * @var string
     *
     * @ORM\Column(name="subject", type="string", length=500, nullable=false)
     */
    private $subject;

    /**
     * @var int
     *
     * @ORM\Column(name="helper_id", type="integer", nullable=false)
     */
    private $helperId;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): self
    {
        $this->title = $title;

        return $this;
    }

    public function getSubject(): ?string
    {
        return $this->subject;
    }

    public function setSubject(string $subject): self
    {
        $this->subject = $subject;

        return $this;
    }

    public function getHelperId(): ?int
    {
        return $this->helperId;
    }

    public function setHelperId(int $helperId): self
    {
        $this->helperId = $helperId;

        return $this;
    }


}
