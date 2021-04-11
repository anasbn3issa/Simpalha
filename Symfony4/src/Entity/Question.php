<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Question
 *
 * @ORM\Table(name="question", indexes={@ORM\Index(name="fk_quizz_id", columns={"quizz_id"})})
 * @ORM\Entity
 */
class Question
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
     * @var int
     *
     * @ORM\Column(name="right_answer", type="integer", nullable=false)
     */
    private $rightAnswer;

    /**
     * @var string
     *
     * @ORM\Column(name="question", type="string", length=500, nullable=false)
     */
    private $question;

    /**
     * @var \Quizz
     *
     * @ORM\ManyToOne(targetEntity="Quizz")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="quizz_id", referencedColumnName="id")
     * })
     */
    private $quizz;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getRightAnswer(): ?int
    {
        return $this->rightAnswer;
    }

    public function setRightAnswer(int $rightAnswer): self
    {
        $this->rightAnswer = $rightAnswer;

        return $this;
    }

    public function getQuestion(): ?string
    {
        return $this->question;
    }

    public function setQuestion(string $question): self
    {
        $this->question = $question;

        return $this;
    }

    public function getQuizz(): ?Quizz
    {
        return $this->quizz;
    }

    public function setQuizz(?Quizz $quizz): self
    {
        $this->quizz = $quizz;

        return $this;
    }


}
