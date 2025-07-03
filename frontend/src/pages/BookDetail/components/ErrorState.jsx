import Alert from '../../../components/Alert'
import Button from '../../../components/Button'
import Card from '../../../components/Card'

function ErrorState({ error }) {
  const handleBack = () => window.history.back()

  return (
    <div className='min-h-screen flex items-center justify-center bg-blue-50 px-4'>
      <Card className='max-w-md w-full'>
        <Alert type='error'>{error}</Alert>
        <div className='mt-4 text-center'>
          <Button
            onClick={handleBack}
            variant='primary'
          >
            뒤로 가기
          </Button>
        </div>
      </Card>
    </div>
  )
}

export default ErrorState
