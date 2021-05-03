<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Answer
 *
 * @ORM\Table(name="answer", indexes={@ORM\Index(name="fk_question_id", columns={"question_id"})})
 * @ORM\Entity(repositoryClass="App\Repository\AnswerRepository")
 */
class Answer
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups("question","answer","quizz")
     */
    private $id;

    /**
     * @var string
     * @Assert\NotBlank
     *
     * @ORM\Column(name="suggestion", type="string", length=1000, nullable=false)
     * @Groups("question","answer","quizz")
     */
    private $suggestion;

    /**
     * @var \Question
     *
     * @ORM\ManyToOne(targetEntity=Question::class, inversedBy="answers")
     * @ORM\JoinColumn(nullable=false)
     * @Groups("answer")
     */
    private $question;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getSuggestion(): ?string
    {
        return $this->suggestion;
    }

    public function setSuggestion(string $suggestion): self
    {
        $this->suggestion = $suggestion;

        return $this;
    }
    public function getQuestion(): ?Question
    {
        return $this->question;
    }

    public function setQuestion(?Question $question): self
    {
        $this->question = $question;
        return $this;
    }


}
