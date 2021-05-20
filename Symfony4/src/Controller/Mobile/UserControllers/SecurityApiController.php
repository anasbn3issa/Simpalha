<?php


namespace App\Controller\Mobile\UserControllers;



use App\Entity\Users;
use App\Service\Mailer;
use App\Service\TokenGenerator;
use App\Repository\UsersRepository;
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
use Symfony\Component\Serializer\SerializerInterface;

/**
 * @Route("/mobile/user", name="security_")
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
     * @Route("/{id}/edit", name="userprofileEdit")
     * @param Request $request
     * @param UserPasswordEncoderInterface $encoder
     * @return RedirectResponse|Response
     * @throws Exception
     */
    public function editUserInfo(Request $request,Users $loggedUser, UserPasswordEncoderInterface $encoder)
    {

        $em = $this->getDoctrine()->getManager();

        $loggedUser->setEmail($request->get('email'));
        $loggedUser->setPseudo($request->get('pseudo'));
	if( $request->get('password')!= ""){
 	 $loggedUser->setPassword($encoder->encodePassword($loggedUser, $request->get('password')));
	}
      
        $em->persist($loggedUser);
        $em->flush();
        return new JsonResponse('User Edited');
    }
 /**
     * @Route("/request-password-api", name="request_password_reset")
     * @param Request $request
     * @param TokenGenerator $tokenGenerator
     * @param Mailer $mailer
     * @return Response
     * @throws \Throwable
     */
    public function requestPasswordResetapi(Request $request, TokenGenerator $tokenGenerator, Mailer $mailer
                                        )
    {


        $email = $request->query->get("email");
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository(Users::class)->findOneBy(['email' => $email]);
        if ($user) {
            $token = $tokenGenerator->generateToken();
            $user->setToken($token);
            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();
            $mailer->sendResetPasswordEmailMessage($user);
            return new JsonResponse("Email has been sent ", 200);

        } else {
            return new JsonResponse("user not found ", 500);

        }

    }

    /**
     * @Route("/helpers", name="helper_index")
     */
    public function helpers(UsersRepository $userRepository, SerializerInterface $serializer): Response
    {
        $helpers = $userRepository->findHelpers();
        $res = $serializer->serialize($helpers, 'json', ['groups'=>'helpers:index']);
        if($res!=null){
            return new JsonResponse(array(
                'status' => 'OK',
                'data' => $res),
                200);
        }
        return new JsonResponse(array(
            'status' => 'ERROR',
            'message'=>"fetch error"),
            500);
    }
}