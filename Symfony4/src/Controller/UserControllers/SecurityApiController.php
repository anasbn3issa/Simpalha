<?php


namespace App\Controller\UserControllers;



use App\Entity\Users;
use Exception;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Normalizer\DateTimeNormalizer;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\RedirectResponse;
use App\Service\FileUploader;

/**
 * @Route("/api", name="security_")
 */
class SecurityApiController extends AbstractController
{


    /**
     * @Route("/login", name="loginApiTest")
     * @param Request $request
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     * @throws ExceptionInterface
     */
    public function login(Request $request, UserPasswordEncoderInterface $encoder): Response
    {
        $email = $request->query->get("email");
        $password = $request->query->get("password");

        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository(Users::class)->findOneBy(['email' => $email]);

        $validPassword = $encoder->isPasswordValid($user, $password);
        if ($user) {
            if ($validPassword) {
                $normalizer = new ObjectNormalizer();
                $serializer = new Serializer(array(new DateTimeNormalizer(), $normalizer));
                $formatted = $serializer->normalize($user);
                return new JsonResponse($formatted);
            } else {
                return new JsonResponse(['error' => "Wrong Password"], 403);
            }
        } else {
            return new JsonResponse(['error' => "Please verify your username or password"], 403);
        }
    }

    /**
     * @Route("/register", name="register_api")
     * @param Request $request
     * @param UserPasswordEncoderInterface $encoder
     * @return JsonResponse
     * @throws ExceptionInterface
     */
    public function register(Request $request, UserPasswordEncoderInterface $encoder): JsonResponse
    {
        if ($this->isGranted('IS_AUTHENTICATED_FULLY')) {
            return new JsonResponse('Already connected');
        }
        $email = $request->get('email');

        $user = new Users();
        $user->setFirstName($request->get('firstName'));
        $user->setLastName($request->get('lastName'));
        $user->setPseudo($request->get('pseudo'));
        $user->setEmail($email);
        $user->setUsername($request->get('pseudo'));
        $user->setDateOfBirth(new \DateTime($request->get('dateOfBirth')));
        $user->setPhone($request->get('phone'));
        $user->setAdresse($request->get('adresse'));
        $user->setAbout($request->get('about'));
        $password = $request->get('password');
        $user->setPassword(
            $encoder->encodePassword(
                $user,
                $password
            )
        );
        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            return new JsonResponse("Please enter a valid email", 500);
        }
        $user->setEmail($request->get('email'));
        $role = array("ROLE_USER");
        $user->setRoles($request->get('roles', $role));
        $user->setIsActive($request->get('isActive', true));
        $em = $this->getDoctrine()->getManager();
        $em->persist($user);
        $em->flush();
        $normalizer = new ObjectNormalizer();
        $serializer = new Serializer(array(new DateTimeNormalizer(), $normalizer));
        $data = $serializer->normalize($user);
        return new JsonResponse("Account Created", 200);
    }

    /**
     * @Route("/logout", name="logout")
     * @throws Exception
     */
    public function logout()
    {
        throw new Exception('Don\'t forget to activate logout in security.yaml');
        return $this->redirectToRoute('security_login');
    }

    /**
     * @Route("/candidatesApi", name="candidates_list")
     *
     * @throws ExceptionInterface
     */
    public function ShowCandidates(): JsonResponse
    {
        $candidate = $this->getDoctrine()->getRepository(Users::class)->findAll();
        $serializer = new Serializer([new DateTimeNormalizer(), new ObjectNormalizer()]);
        $data = $serializer->normalize($candidate, null, array('attributes' => array('firstName', 'lastName', 'dateOfBirth'
        , 'phone', 'adresse', 'professionalTitle')));
        return new JsonResponse($data);
    }

    /**
     * @Route("/editProfileApi", name="candidateprofileEdit")
     * @param Request $request
     * @param FileUploader $fileUploader
     * @param UserPasswordEncoderInterface $encoder
     * @return RedirectResponse|Response
     * @throws Exception
     */
    public function editUserInfo(Request $request, FileUploader $fileUploader, UserPasswordEncoderInterface $encoder)
    {
        if (!$this->isGranted('IS_AUTHENTICATED_FULLY')) {
            return new JsonResponse('Please SignIn');
        }
        $em = $this->getDoctrine()->getManager();
        $user = $this->getUser();
        $loggedUser = $this->getDoctrine()->getRepository(Users::class)->find($user);
        $loggedUser->setEmail($request->get('email'));
        $loggedUser->setFirstName($request->get('firstName'));
        $loggedUser->setLastName($request->get('lastName'));
        $loggedUser->setDateOfBirth(new \DateTime($request->get('dateOfBirth')));
        $loggedUser->setPhone($request->get('phone'));
        $loggedUser->setAdresse($request->get('adresse'));
        $loggedUser->setProfessionalTitle($request->get('professionalTitle'));
        $user->setPassword($encoder->encodePassword($user, $user->getPassword()));
        if ($request->isMethod('post')) {
            /**
             * @var FileUploader $image
             */
            $image = $request->get('imageName')->getData();
            if ($image) {
                $newFilename = $fileUploader->upload($image);
                $user->setImageName($newFilename);
            }
        }
        $em->persist($user);
        $em->flush();
        return new JsonResponse('User Edited');
    }
}