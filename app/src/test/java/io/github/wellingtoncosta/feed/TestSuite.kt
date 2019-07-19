package io.github.wellingtoncosta.feed

import io.github.wellingtoncosta.feed.interactor.PostInteractorTest
import io.github.wellingtoncosta.feed.repository.PostRepositoryTest
import io.github.wellingtoncosta.feed.repository.UserRepositoryTest
import io.github.wellingtoncosta.feed.viewmodel.ListPostsViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    ListPostsViewModelTest::class,
    PostInteractorTest::class,
    PostRepositoryTest::class,
    UserRepositoryTest::class
)
class TestSuite
