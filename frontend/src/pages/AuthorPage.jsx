import AuthorProfile from '../components/AuthorProfile'
import ManuscriptList from '../components/ManuscriptList'

function AuthorPage() {
  return (
    <div style={{maxWidth: '1200px', margin: '140px auto'}}>
      <AuthorProfile />
      <ManuscriptList />
    </div>
  )
}

export default AuthorPage 