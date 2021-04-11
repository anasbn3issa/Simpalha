<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * QuizzResult
 *
 * @ORM\Table(name="quizz_result", indexes={@ORM\Index(name="fk_result_quizz_id", columns={"quizz_id"})})
 * @ORM\Entity
 */
class QuizzResult
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
     * @ORM\Column(name="result", type="integer", nullable=false)
     */
    private $result;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="result_date", type="datetime", nullable=false)
     */
    private $resultDate;

    /**
     * @var int
     *
     * @ORM\Column(name="student_id", type="integer", nullable=false)
     */
    private $studentId;

    /**
     * @var int
     *
     * @ORM\Column(name="quizz_id", type="integer", nullable=false)
     */
    private $quizzId;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getResult(): ?int
    {
        return $this->result;
    }

    public function setResult(int $result): self
    {
        $this->result = $result;

        return $this;
    }

    public function getResultDate(): ?\DateTimeInterface
    {
        return $this->resultDate;
    }

    public function setResultDate(\DateTimeInterface $resultDate): self
    {
        $this->resultDate = $resultDate;

        return $this;
    }

    public function getStudentId(): ?int
    {
        return $this->studentId;
    }

    public function setStudentId(int $studentId): self
    {
        $this->studentId = $studentId;

        return $this;
    }

    public function getQuizzId(): ?int
    {
        return $this->quizzId;
    }

    public function setQuizzId(int $quizzId): self
    {
        $this->quizzId = $quizzId;

        return $this;
    }


}
